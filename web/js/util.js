function handleResponse(status) {
    var gifLoad = document.getElementById('loadingGIF');
    if (status === 1) {
        PF('statusDialog').show();
//        gifLoad.style.display = "block";
    } else if (status === 2) {
        PF('statusDialog').hide();
//        gifLoad.style.display = "none";
    }
}

function setFilter(filter) {
    $("input[name='formLoad:tableInfo:columCategory:filter']").val(filter).keyup();
}

$(document).ready(function () {
    if ($("#menuPrincipal").length > 0) {
        setTimeout(function () {
            showNotifications();
        }, 10000);
    }
    $(".ui-outputlabel-rfi").hide();
});

function hideAsteriscos() {
    $(".ui-outputlabel-rfi").hide();
}

function showNotifications() {
    setTimeout(function () {
        PF('notificationPanel').show();
        setTimeout(function () {
            PF('notificationPanel').hide();
        }, 10000);
    }, 10000);
}
