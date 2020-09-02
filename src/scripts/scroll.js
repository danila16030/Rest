(function () {
    buttonControl();

    function activateButton(button) {
        button.disabled = "";
        button.style.visibility = "visible"
    }

    function disableButton(button) {
        button.disabled = "disabled";
        button.style.visibility = "hidden"
    }

    function buttonControl() {
        let scrollHeight = getScrollHeight();
        let activeButton;
        let disabledButton;
        if (scrollHeight / 2 < window.pageYOffset + document.documentElement.clientHeight / 2) {
            activeButton = document.getElementById('top');
            disabledButton = document.getElementById('bottom');
        } else {
            activeButton = document.getElementById('bottom');
            disabledButton = document.getElementById('top');
        }
        activateButton(activeButton);
        disableButton(disabledButton);

        setTimeout(function () {
            buttonControl()
        }, 500);
    }
})();

function getScrollHeight() {
    return Math.max(
        document.body.scrollHeight, document.documentElement.scrollHeight,
        document.body.offsetHeight, document.documentElement.offsetHeight,
        document.body.clientHeight, document.documentElement.clientHeight
    );
}

function inBottom(scrollHeight) {
    return scrollHeight < window.pageYOffset + document.documentElement.clientHeight + document.documentElement.clientHeight / 3.8
}