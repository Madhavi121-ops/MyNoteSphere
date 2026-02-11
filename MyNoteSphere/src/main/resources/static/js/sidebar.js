document.addEventListener("DOMContentLoaded", function () {
    const sidebar = document.getElementById("sidebar");
    const toggleBtn = document.getElementById("sidebarToggle");
    const mainContent = document.querySelector(".main-content");

    if (toggleBtn) {
        toggleBtn.addEventListener("click", function () {
            // Toggle sidebar state
            sidebar.classList.toggle("open");

            // Toggle Icon and Button Position/Style
            if (sidebar.classList.contains("open")) {
                toggleBtn.innerHTML = "&times;"; // Change to Close (X)
                toggleBtn.classList.add("toggled"); // Add class for styling (e.g. fixed position)

                if (window.innerWidth > 768) {
                    sidebar.style.width = "300px";
                    mainContent.style.marginLeft = "300px";
                    mainContent.style.width = "calc(100% - 300px)";
                }
            } else {
                toggleBtn.innerHTML = "☰"; // Change back to Hamburger
                toggleBtn.classList.remove("toggled");

                if (window.innerWidth > 768) {
                    sidebar.style.width = "0";
                    mainContent.style.marginLeft = "0";
                    mainContent.style.width = "100%";
                }
            }
        });
    }
});

// Sidebar User Dropdown Toggle
function toggleSidebarDropdown() {
    const dropdown = document.getElementById('sidebarDropdown');
    const header = document.querySelector('.profile-header');
    const arrow = document.querySelector('.dropdown-arrow');

    if (dropdown.style.display === 'none' || dropdown.style.display === '') {
        dropdown.style.display = 'block';
        header.classList.add('active');
    } else {
        dropdown.style.display = 'none';
        header.classList.remove('active');
    }
}
