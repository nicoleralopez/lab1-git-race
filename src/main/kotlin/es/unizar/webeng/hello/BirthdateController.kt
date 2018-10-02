package es.unizar.webeng.hello

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import khttp.get
import org.json.*
import java.net.URLEncoder

@RestController
class BirthdateController {
  /**
   *
   * This returns a json with the birthdate of the given person. If the given
   * person does not exist in dbpedia, returns error 404.
   * @returns the birthdate of the given person
   */
  @RequestMapping("/birthdate/{person}")
  fun birthdate(@ModelAttribute msg: Message, @PathVariable person: String): Birthdate {
      var sparqlQuery = URLEncoder.encode("""
                          PREFIX dbo:<http://dbpedia.org/ontology/>
                          PREFIX res:<http://dbpedia.org/resource/>
                          SELECT ?birthDate WHERE {
                              res:$person dbo:birthDate ?birthDate .
                              res:$person rdf:type dbo:Person
                          }
                        """, "UTF-8")
      val response = get("http://dbpedia.org/sparql?default-graph-uri=&query=$sparqlQuery&format=json")
      var statusCode = response.statusCode
      if( statusCode == 200) {
        try {
          val birthDate = (((((response.jsonObject.get("results") as JSONObject)
                          .get("bindings") as JSONArray).get(0) as JSONObject)
                          .get("birthDate") as JSONObject).get("value").toString())
          return Birthdate(birthDate)
        }
        catch(e:JSONException) {
          // Either the given person does not exist in dbpedia or the birthday
          // of the person is not stored in dbpedia, we have a JSONException
          msg.message = "Error 404 NOT FOUND"
          throw URINotFoundException(msg)
        }
      }
      else {
          msg.message = "Error $statusCode dbpedia not working!"
          throw URINotFoundException(msg)
      }
  }
}
