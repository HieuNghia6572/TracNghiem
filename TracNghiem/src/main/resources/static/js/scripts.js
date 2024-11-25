

    document.querySelectorAll('.toggle-submenu').forEach(function (link) {
        link.addEventListener('click', function (event) {
            event.preventDefault(); // Ngăn chặn hành vi mặc định của liên kết
            const submenu = this.nextElementSibling; // Lấy submenu ngay sau thẻ <a>

            // Kiểm tra xem submenu có đang hiển thị không
            if (submenu.style.display === 'block') {
                submenu.style.display = 'none'; // Ẩn submenu
            } else {
                submenu.style.display = 'block'; // Hiện submenu
            }
        });
    });
    // Thay đổi trạng thái của sidebar khi nhấn nút
    let isHidden = false;
    const sidebar = document.getElementById('sidebar');
    const content = document.getElementById('content');
    const area = document.getElementById('area'); // Lấy phần tử popular_catagory_area
    const toggleButton = document.getElementById('toggleButton');

    toggleButton.addEventListener('click', () => {
        isHidden = !isHidden;

        if (isHidden) {
            sidebar.classList.add('sidebar-hidden');
            content.classList.add('content-hidden');
            area.classList.add('area-hidden'); // Thêm lớp để ẩn popular category
            toggleButton.innerHTML = '&#9776;'; // Biểu tượng nút
            toggleButton.classList.add('active'); // Thêm lớp active
        } else {
            sidebar.classList.remove('sidebar-hidden');
            content.classList.remove('content-hidden');
            area.classList.remove('area-hidden'); // Xóa lớp để hiện popular category
            toggleButton.innerHTML = '&#9776;'; // Biểu tượng nút
            toggleButton.classList.remove('active'); // Xóa lớp active
        }
    });

    // Lấy tất cả các icon-box
    const iconBoxes = document.querySelectorAll('.icon-box');
    const notification = document.querySelector('.notification');
    const overlay = document.querySelector('.overlay');
    const notificationMessage = document.getElementById('notification-message');
    const closeButton = document.querySelector('.close-btn');

    // Hàm hiển thị thông báo
    function showNotification(message) {
        notificationMessage.textContent = message;
        notification.style.display = 'block';
        overlay.style.display = 'block';
    }

    // Thêm sự kiện click cho mỗi icon-box
    iconBoxes.forEach(box => {
        box.addEventListener('click', () => {
            const message = box.getAttribute('data-message');
            showNotification(message);
        });
    });

    // Đóng thông báo
    closeButton.addEventListener('click', () => {
        notification.style.display = 'none';
        overlay.style.display = 'none';
    });

    // Đóng thông báo khi click vào overlay
    overlay.addEventListener('click', () => {
        notification.style.display = 'none';
        overlay.style.display = 'none';
    });


    // Các script hiện tại khác của bạn

    window.addEventListener('scroll', function() {
        const header = document.querySelector('.header-area');
        if (window.scrollY > 50) { // Thay đổi giá trị 50 nếu cần
            header.classList.add('header-hidden');
        } else {
            header.classList.remove('header-hidden');
        }
    });

    let remainingTime; // thời gian còn lại nhận từ server

    // Hàm này sẽ được gọi khi trang được tải
    window.onload = function () {
        fetch('/time-remaining') // Lấy thời gian còn lại từ server
            .then(response => response.json())
            .then(data => {
                remainingTime = data.remainingTime;
                startCountdown();
            });
    };

    function startCountdown() {
        let countdownElement = document.getElementById("countdown");
        let interval = setInterval(function () {
            if (remainingTime <= 0) {
                clearInterval(interval);
                alert("Hết giờ!");
            } else {
                remainingTime--;
                countdownElement.innerHTML = formatTime(remainingTime);
            }
        }, 1000);
    }

    function formatTime(seconds) {
        let minutes = Math.floor(seconds / 60);
        let secs = seconds % 60;
        return `${minutes.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}`;
    }

    function fetchTime() {
        fetch('/time')
            .then(response => response.text())
            .then(data => {
                document.getElementById('time').innerText = data;
            });
    }

    setInterval(fetchTime, 1000); // Lấy thời gian mỗi giây
