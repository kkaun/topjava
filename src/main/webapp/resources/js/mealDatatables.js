/**
 * Created by Кира on 22.05.2017.
 */

var ajaxUrl = "/ajax/meals";
var datatableApi;

$(function(){
    datatableApi = $("#mealsDatatable").DataTable({

        "paging": false,
        "info": true,
        "columns":[

            {
                "data": "description"
            },
            {
                "data": "datetime"
            },
            {
                "data": "calories"
            },
            {
                "data": "exceeded"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order":
            [
                0, "asc"
            ]
        }
    );

    makeEditable();

    }
);