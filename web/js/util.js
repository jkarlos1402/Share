function handleResponse(status) {
    if (status === 1) {
        PF('statusDialog').show();
        PF('pbAjax').start();
    } else if (status === 2) {
        PF('pbAjax').cancel();
        PF('statusDialog').hide();
    }
}

function setFilter(filter) {
    $("input[name='formLoad:tableInfo:columCategory:filter']").val(filter).keyup();
}

$(document).ready(function () {
    if ($("#menuPrincipal").length > 0 && ($("#proyectoUser").val() !== "0" || $("#rolUser").val() === "1")) {
        initMenu();
        setTimeout(function () {
            showNotifications();
        }, 10000);
    }
    $(".ui-outputlabel-rfi").hide();
});

function initMenu() {
    $('#menuGeneral').dropdown_menu({
        sub_indicators: true,
        vertical: true
    });
}

function hideAsteriscos() {
    $(".ui-outputlabel-rfi").hide();
}

function showNotifications() {
    if ($("#menuPrincipal").length > 0 && ($("#proyectoUser").val().indexOf("2") !== -1 || $("#rolUser").val() === "1")) {
        setTimeout(function () {
            PF('notificationPanel').show();
            setTimeout(function () {
                PF('notificationPanel').hide();
            }, 10000);
        }, 10000);
    }
}

function showLoading(status) {    
    if ($("#showLoading").val() === "1" && status === "1") {
        PF('statusDialog').show();
        PF('pbAjax').start();

    } else if (status === "0") {
        PF('pbAjax').cancel();
        PF('statusDialog').hide();
    }
}

