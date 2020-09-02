(function a() {
    addLister();

    function addLister() {
        let element;
        element = document.getElementById('search');
        element.addEventListener('input', cleanTable);

        element = document.getElementById('top');
        element.addEventListener("click", goToTop);

        element = document.getElementById('bottom');
        element.addEventListener("click", goToBottom);

    }

    function cleanTable() {
        let table = document.getElementById("table");
        table.innerHTML = "";
    }

    function goToTop() {
        window.scrollTo(0, 0);
    }

    function goToBottom() {
        window.scrollTo(0, getScrollHeight() - document.documentElement.clientHeight - document.documentElement.clientHeight / 3.8);
    }
})();