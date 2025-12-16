document.addEventListener("DOMContentLoaded", () => {

    const isDarkMode =
        window.matchMedia &&
        window.matchMedia('(prefers-color-scheme: dark)').matches;

    tinymce.init({
		    selector: '#editor',
		    min_height: 300,
		    max_height: 800,
		    autoresize_bottom_margin: 20,
		    plugins: 'autoresize',
		

        menubar: false,
        branding: false,

        // 🔥 AUTO DARK MODE
        skin: isDarkMode ? 'oxide-dark' : 'oxide',
        content_css: isDarkMode ? 'dark' : 'default',

        plugins: [
            'lists',
            'link',
            'image',
            'table',
            'code'
        ],

        toolbar:
            'undo redo | bold italic underline | ' +
            'bullist numlist | link image table | code'
    });

});
