document.addEventListener('DOMContentLoaded', function () {
    const overlay = document.getElementById('addTaskOverlay');
    const openBtns = document.querySelectorAll('[data-open-add-task]');
    const closeBtns = document.querySelectorAll('[data-close-add-task]');
    const sidebar = document.getElementById('sidebar');
    const sidebarToggle = document.getElementById('sidebarToggle');
    const titleInput = document.getElementById('taskTitleInput');

    function openModal() {
        overlay.classList.add('open');
        setTimeout(() => titleInput && titleInput.focus(), 50);
    }

    function closeModal() {
        overlay.classList.remove('open');
    }

    openBtns.forEach(btn => btn.addEventListener('click', openModal));
    closeBtns.forEach(btn => btn.addEventListener('click', closeModal));

    if (overlay) {
        overlay.addEventListener('click', function (e) {
            if (e.target === overlay) closeModal();
        });
    }

    document.addEventListener('keydown', function (e) {
        if (e.key === 'Escape') closeModal();
    });

    if (sidebarToggle && sidebar) {
        sidebarToggle.addEventListener('click', function () {
            sidebar.classList.toggle('collapsed');
        });
    }

    // On small screens, start with the sidebar collapsed (it behaves as an overlay drawer there)
    function applyResponsiveSidebar() {
        if (window.innerWidth <= 768) {
            sidebar.classList.add('collapsed');
        } else {
            sidebar.classList.remove('collapsed');
        }
    }
    applyResponsiveSidebar();
    window.addEventListener('resize', applyResponsiveSidebar);

    // Close the sidebar drawer when a nav link is tapped on mobile
    if (sidebar) {
        sidebar.querySelectorAll('a').forEach(link => {
            link.addEventListener('click', function () {
                if (window.innerWidth <= 768) {
                    sidebar.classList.add('collapsed');
                }
            });
        });
    }

    // Quick keyboard shortcut: "q" opens add task, like Todoist
    document.addEventListener('keydown', function (e) {
        if (e.key.toLowerCase() === 'q' && !overlay.classList.contains('open')
            && document.activeElement.tagName !== 'INPUT'
            && document.activeElement.tagName !== 'TEXTAREA') {
            openModal();
        }
    });
});
