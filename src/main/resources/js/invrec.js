// When using the input file job search, users obviously must have uploaded a file first. These functions are to be bound into the fileuploader callbacks.

// When at least 1 file has successfully been uploaded, then the search button is usable
function fileUploadSuccessCallback() {
    $('#getjobs').prop('disabled', false);
}

//If the user has uploaded some files but then deletes them all, hence leaving no uploaded files, then we must disabled the search button again until they upload at least 1 more file.
function fileUploadFileDeletion() {
    $('#getjobs').prop('disabled', true);
}

$('#supplierselectfake').on('click', function() {
    $('#presupplieralert').hide();
    $('#getjobs, #hypotheticalfileformaterror').prop('disabled', false);
    $('#postsupplieralert, #suppliermapping').show();
    metaboot($('#suppliermapping'));
});

function hiddentextfix(targettext, targetcontainer) {
    var p = targettext;
    var divh = targetcontainer.height();
    while ($(p).outerHeight() > divh) {
        $(p).text(function(index, text) {
            return text.replace(/\W*\s(\S)*$/, '...');
        });
    }
}

var optionarray = [
    {"id": '1', "value": 'Option 1'},
    {"id": '2', "value": 'Option 2'},
    {"id": '3', "value": 'Option 3'},
    {"id": '4', "value": 'Option 4'},
    {"id": '5', "value": 'Option 5'}
];

function updateOptionsArray() {
    var selects = $('.ourcolumnmapping');
    for (var i = 0; i < optionarray.length; i++) {
        optionarray[i].used = false;
    }
    for (var j = 0; j < selects.length; j++) {
        for (var l = 0; l < optionarray.length; l++) {
            if ($(selects[j]).val() === optionarray[l].id) {
                optionarray[l].used = true;
            }
        }
    }
    var optionsstring = generateOptions();
    for (var k = 0; k < selects.length; k++) {
        $(selects[k]).find('option:not(:selected), option[value=""]').remove();
        $(selects[k]).append(optionsstring);
    }
}

function generateOptions() {
    var optionsstring = '<option value="">Select a column</option>';
    for (var i = 0; i < optionarray.length; i++) {
        if (!optionarray[i].used) {
            optionsstring = optionsstring + '<option value="' + optionarray[i].id + '">' + optionarray[i].value + '</option>';
        }
    }
    return optionsstring;
}

function mappingGenerator() {
    var optionsstring = generateOptions();
    var template = '<div class="mappingwrapper">\n\
<div class="control-group">\n\
<label class="control-label"></label>\n\
<div class="controls">\n\
<select class="input-medium include ourcolumnmapping">' + optionsstring + '</select>\n\
</div>\n\
</div>\n\
<div class="control-group nodisplay">\n\
<label class="control-label"></label>\n\
<div class="controls">\n\
<input type="text" class="include input-large theircolumnmapping">\n\
</div>\n\
</div>\n\
</div>';
    $('#suppliermapping').append(template);
    metaboot($('#suppliermapping div.control-group:last-child'));
    relabel();
}

function relabel() {
    var us = "";
    var them = "";
    if (document.getElementById('vertical').checked) {
        us = $('#suppliermapping').data('us-vertical') + ":";
        them = $('#suppliermapping').data('them-vertical') + ":";
    }
    else {
        us = $('#suppliermapping').data('us-horizontal') + ":";
        them = $('#suppliermapping').data('them-horizontal') + ":";
    }
    $('.mappingwrapper div.control-group:has(".ourcolumnmapping") label').text(us);
    $('.mappingwrapper div.control-group:has(".theircolumnmapping") label').text(them);
}

$(document).ready(function() {

    $('#vertical').on('change', function() {
        relabel();
    });

    mappingGenerator();

    $('#suppliermapping').on('change', '.ourcolumnmapping, .theircolumnmapping', function(event) {
        var parents = $(this).parents('div.mappingwrapper');
        var us = parents.find('.ourcolumnmapping');
        var them = parents.find('.theircolumnmapping');
        var nomappings = $('#suppliermapping').find('.ourcolumnmapping').length;
        updateOptionsArray();
        if (us.val() !== '') {
            them.parents('div.control-group').show();
            if (them.val() !== '' && !$('.theircolumnmapping:hidden').length > 0 && nomappings < optionarray.length) {
                mappingGenerator();
            }
        }
        else {
            if (us.val() === '' && nomappings > 1) {
                if (nomappings === optionarray.length) {
                    mappingGenerator();
                }
                parents.remove();
            }
        }
    });

    $('#notificationdropdown').on('click', function() {
        $(this).find('.notificationblob').toggleClass('full');
    });

    $('button.confirmmatch').on('click', function() {
        $(this).parents('tr').toggleClass('success');
        if ($(this).parents('.DTFC_RightWrapper').length > 0 || $(this).parents('.DTFC_LeftWrapper').length > 0) {
            var allrows = $(this).parents('tbody').find('tr');
            var rowno = allrows.index($(this).parents('tr'));
            rowno = rowno + 1;
            $(this).parents('.DTFC_ScrollWrapper').find('.dataTables_scroll .dataTables_scrollBody tbody tr:nth-child(' + rowno + ')').toggleClass('success');
        }
        return false;
    });

    $('button.flagforapproval').on('click', function() {
        $(this).parents('tr').removeClass('success').toggleClass('warning');
        if ($(this).parents('.DTFC_RightWrapper').length > 0 || $(this).parents('.DTFC_LeftWrapper').length > 0) {
            var allrows = $(this).parents('tbody').find('tr');
            var rowno = allrows.index($(this).parents('tr'));
            rowno = rowno + 1;
            $(this).parents('.DTFC_ScrollWrapper').find('.dataTables_scroll .dataTables_scrollBody tbody tr:nth-child(' + rowno + ')').removeClass('success').toggleClass('warning');
        }
        return false;
    });

    $('#myTab a').click(function(e) {
        e.preventDefault();
        $(this).tab('show');
    }).on('shown', function(e) {
        var tabcontent = $(e.target).attr('href');
        $(tabcontent).find('.dataTables_scrollBody .invoicetables').each(function() {
//            $(this).DataTable().draw();
            $(this).dataTable().fnAdjustColumnSizing();
//            new $.fn.dataTable.FixedColumns($(this).DataTable(), {
//                "iRightColumns": 3,
//                "iLeftColumns": 0
//            });

//            new $.fn.dataTable.FixedColumns($(this).DataTable()).fnUpdate().fnRedrawLayout();

        });
    });


    //FAKE CODE! ONLY TO HELP DISPLAY DESIRED BEHAVIOUR! REPLACE FOR LIVE VERSION!!!!

    $('#hypotheticalfileformaterror').on('click', function(e) {
        e.preventDefault();
        $('#uploadfailajax').show();
    });

    $('#getjobs').on('click', function(e) {
        e.preventDefault();
        $('#uploadfailajax').hide();
        //        if ($('#mainform').valid()) {
        if ($('#jobsearchmethodradiosinputfile').is(':checked')) {
            $('#jobsearchajaxsection').hide();
            $('#loadingmask2').show();
            setTimeout(function() {
                $('#inputfileajaxsection').show();
                $('#loadingmask2').hide();
                $('#inputfileajaxsection').find('.dataTables_scrollBody .invoicetables').each(function() {
                    $(this).dataTable().fnAdjustColumnSizing();
                });
            }, 3000);
        }
        else {
            if ($('#jobsearchmethodradiosjobsearch').is(':checked')) {
                $('#inputfileajaxsection').hide();
                $('#loadingmask2').show();
                setTimeout(function() {
                    $('#jobsearchajaxsection').show();
                    $('#loadingmask2').hide();
                    $('#jobsearchajaxsection').find('.dataTables_scrollBody .invoicetables').each(function() {
                        $(this).dataTable().fnAdjustColumnSizing();
                    });
                }, 3000);
            }
        }
//        }
    });



});