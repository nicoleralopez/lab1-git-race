/**
 * Create HTML table row.
 *
 * \param text (str) HTML code to be placed inside the row.
 */
function tr(text) {
    return '<tr>' + text + '</tr>';
}

/**
 * Create HTML table cell element.
 *
 * \param text (str) The text to be placed inside the cell.
 */
function td(text) {
    return '<td>' + text + '</td>';
}

/**
 * Edit value: load key and value into inputs.
 */
function editButton(key) {
    var format = '<button '
        + 'class="btn btn-default" '
        + 'onclick="editKey(\'{key}\')" '
        + '>Edit</button>';
    return format.replace(/{key}/g, key);
}

/**
 * Create HTML button with which a key-value pair is deleted from persistence.
 *
 * \param key (str) that'll be deleted when the created button is clicked.
 */
function deleteButton(key) {
    var format = '<button '
        + 'class="btn btn-default" '
        + 'data-key="{key}" '
        + 'onclick="deleteKey(\'{key}\')" '
        + '>Delete</button>'
    return format.replace(/{key}/g, key);
}

/**
 * See information key: Load information
 */
function seeButton(key) {
    var format = '<button '
        + 'class="btn btn-default" '
        + 'onclick="seeKey(\'{key}\')" '
        + '>See</button>';
    return format.replace(/{key}/g, key);
}

/**
 * Create HTML table row element.
 *
 * \param key (str) text into the key cell.
 * \param value (str) text into the value cell.
 */
function row(key, value) {
    return $(
        tr(
            td(key) +
            td(value) +
            td(editButton(key)) +
            td(deleteButton(key)) +
            td(seeButton(key))));
}

/**
 * Create HTML table row element.
 *
 * \param key (str) text into the key cell.
 * \param value (str) text into the value cell.
 * \param description (str) text into the value cell.
 */
function rowd(key, value, description) {
    return $(
        tr(
            tr(td("KEY")+td("VALUE")+td("DESCRIPTION"))+
            td(key) +
            td(value) +
            td(description)
        )

    );
}

/**
 * Clear and reload the values in data table.
 */
function refreshTable() {
    $.get('/values', function(data) {
        var attr,
            mainTable = $('#mainTable tbody');
        mainTable.empty();
        for (attr in data) {
            if (data.hasOwnProperty(attr)) {
                mainTable.append(row(attr, data[attr]));
            }
        }
    });
}

/**
 * Edit Movie
 *
 * \param key (str). The key to be deleted.
 */

function editKey(key) {
    /* Find the row with key in first column (key column). */
    var format = '#mainTable tbody td:first-child:contains("{key}")',
        selector = format.replace(/{key}/, key),
        cells = $(selector).parent().children(),
        key = cells[0].textContent

    /*Find information key (value,description)*/
    $.get('/see',{key:key}, function(data) {
        var attr
        for (attr in data) {
            if (data.hasOwnProperty(attr)) {
              value = attr;
              description = data[attr];
              keyInput = $('#keyInput'),
              valueInput = $('#valueInput'),
              descrip = $('#descrip');

              /* Load the key and value texts into inputs
               * Select value text so it can be directly typed to
               */
              keyInput.val(key);
              valueInput.val(value);
              descrip.val(description);
              valueInput.select();
            }
        }

    });


}


/**
 * Delete key-value pair.
 *
 * \param key (str) The key to be deleted.
 */
function deleteKey(key) {
    /*
     * Delete the key.
     * Reload keys and values in table to reflect the deleted ones.
     * Set keyboard focus to key input: ready to start typing.
     */
    $.post('/delete', {key: key}, function() {
        refreshTable();
        $('#keyInput').focus();
    });
}

/**
 * See information of key-value pair.
 *
 * \param key (str) The key to be which you want see the information.
 */
function seeKey(key) {

  $.get('/see',{key:key}, function(data) {
      var attr,
          infoTable = $('#infoTable tbody');
      infoTable.empty();
      for (attr in data) {
          if (data.hasOwnProperty(attr)) {
              infoTable.append(rowd(key,attr, data[attr]));
          }
      }
  });

}

$(document).ready(function() {
  var keyInput = $('#keyInput'),
      valueInput = $('#valueInput'),
      descrip = $('#descrip');

  refreshTable();
  $('#addForm').on('submit', function(event) {
      var data = {
          "key": keyInput.val(),
          "value": valueInput.val(),
          "description": descrip.val()
      };

      /*
       * Persist the new key-value pair.
       * Clear the inputs.
       * Set keyboard focus to key input: ready to start typing.
       */
      $.post('/add', data, function() {
          refreshTable();
          keyInput.val('');
          valueInput.val('');
          descrip.val('');

          keyInput.focus();
      });
      /* Prevent HTTP form submit */
      event.preventDefault();
  });

    /* Focus keyboard input into key input; ready to start typing */
    keyInput.focus();
});
