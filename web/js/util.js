function handleResponse(status) {
    var gifLoad = document.getElementById('loadingGIF');
    if (status === 1) {
        gifLoad.style.display = "block";
    } else if (status === 2) {
        gifLoad.style.display = "none";
    }
}

function setFilter(filter) {
    $("input[name='formLoad:tableInfo:columCategory:filter']").val(filter).keyup();
}

$(document).ready(function () {
//    setTimeout(function () {
//        showNotifications();
//    }, 10000);
});

function showNotifications() {
//    setTimeout(function () {
//        PF('notificationPanel').show();
//        setTimeout(function () {
//            PF('notificationPanel').hide();
//        }, 10000);
//    }, 10000);
}

