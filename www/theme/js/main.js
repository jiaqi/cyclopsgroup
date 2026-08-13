$(document).ready(function () {
    const toggleBtn = $('#side-panel-toggle');
    const mobileWidth = 1000;

    function updateIcon() {
        const isMobile = $(window).width() <= mobileWidth;
        const isToggled = $('body').hasClass('sidebar-toggled');
        const isHidden = isMobile ? !isToggled : isToggled;

        if (isHidden) {
            toggleBtn.text('menu');
        } else {
            toggleBtn.text('fullscreen');
        }
    }

    updateIcon();

    let wasMobile = $(window).width() <= mobileWidth;
    $(window).on('resize', function () {
        const isMobile = $(window).width() <= mobileWidth;
        if (wasMobile !== isMobile) {
            // When crossing the mobile/desktop boundary, reset the toggle state.
            // This ensures it defaults to hidden on mobile, and open on desktop,
            // preventing inverted states from persisting across layouts.
            $('body').removeClass('sidebar-toggled');
            wasMobile = isMobile;
        }
        updateIcon();
    });

    toggleBtn.on('click', function () {
        $('body').toggleClass('sidebar-toggled');
        updateIcon();
    });

    $(document).on('click', function (e) {
        const isMobile = $(window).width() <= mobileWidth;
        const isToggled = $('body').hasClass('sidebar-toggled');
        if (isMobile && isToggled) {
            if (!$(e.target).closest('.wy-nav-side').length && !$(e.target).closest('#side-panel-toggle').length) {
                $('body').removeClass('sidebar-toggled');
                updateIcon();
            }
        }
    });
});