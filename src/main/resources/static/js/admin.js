        
        //관리자페이지 side메뉴바
        $(document).ready(function () {
            const sidebarToggle = $('#sidebarToggle');
            if (sidebarToggle) {
                sidebarToggle.click(function () {
                    console.log('버튼클릭');
                    event.preventDefault(); /* 아래쪽 요소로 이벤트가 전파 안되도록 */
                    document.body.classList.toggle('sb-sidenav-toggled');
                    localStorage.setItem('sb|sidebar-toggle', document.body.classList.contains('sb-sidenav-toggled'));
                });
            }
        });