
var form;


function formatDate(date) {
    return date.replace('T', ' ').substr(0, 16);
}


function makeEditable() {
    $('.delete').click(function () {
        deleteRow($(this).attr("id"));
    });

    $('#detailsForm').submit(function () {
        save();
        return false;
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(event, jqXHR, options, jsExc);
    });
}

function add() {
    $('#id').val(null);
    $('#editRow').modal();
}

function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: 'DELETE',
        success: function () {
            updateTable();
            successNoty('Deleted');
        }
    });
}

function updateTable() {
    $.get(ajaxUrl, function (data) {
        datatableApi.clear();
        $.each(data, function (key, item) {
            datatableApi.row.add(item);
        });
        datatableApi.draw();
    });
}

function save() {
    var form = $('#detailsForm');
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: function () {
            $('#editRow').modal('hide');
            updateTable();
            successNoty('Saved');
        }
    });
}

var failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(text) {
    closeNoty();
    noty({
        text: text,
        type: 'success',
        layout: 'bottomRight',
        timeout: 1500
    });
}

function failNoty(event, jqXHR, options, jsExc) {
    closeNoty();
    failedNote = noty({
        text: 'Failed: ' + jqXHR.statusText + "<br>",
        type: 'error',
        layout: 'bottomRight'
    });
}




//
// function updateRow(id) {
//     $.get(ajaxUrl + id, function (data) {
//         $.each(data, function (key, value) {
//             form.find("input[name='" + key + "']").val(
//                 key === "dateTime" ? formatDate(value) : value
//             );
//         });
//         $('#editRow').modal();
//     });
// }


//
// function renderEditBtn(data, type, row) {
//     if (type == 'display') {
//         return '<a class="btn btn-xs btn-primary" onclick="updateRow(' + row.id + ');">' +
//             '<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>';
//     }
// }
//
// function renderDeleteBtn(data, type, row) {
//     if (type == 'display') {
//         return '<a class="btn btn-xs btn-danger" onclick="deleteRow(' + row.id + ');">'+
//             '<span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>';
//     }
// }
//
//
//
//
// function updateTableByData(data) {
//     datatableApi.clear().rows.add(data).draw();
// }
//
// // https://api.jquery.com/jquery.extend/#jQuery-extend-deep-target-object1-objectN
// function extendsOpts(opts) {
//     $.extend(true, opts,
//         {
//             "ajax": {
//                 "url": ajaxUrl,
//                 "dataSrc": ""
//             },
//             "paging": false,
//             "info": true,
//             // "language": {
//             //     "search": i18n["common.search"]
//             // },
//             "initComplete": makeEditable
//         }
//     );
//     return opts;
// }
