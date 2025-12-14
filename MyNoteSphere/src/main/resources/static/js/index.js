// src/main/resources/static/js/index.js
document.addEventListener('DOMContentLoaded', function () {
  // Check ClassicEditor loaded by CDN
  if (typeof ClassicEditor === 'undefined') {
    console.error('ClassicEditor is not loaded. Make sure CDN script is included before this file.');
    return;
  }

  // CSRF token handling (if using Spring Security)
  const csrfMeta = document.querySelector('meta[name="_csrf"]');
  const csrfHeaderMeta = document.querySelector('meta[name="_csrf_header"]');
  const csrfToken = csrfMeta ? csrfMeta.getAttribute('content') : null;
  const csrfHeader = csrfHeaderMeta ? csrfHeaderMeta.getAttribute('content') : null;

  let editorInstance = null;

  ClassicEditor
    .create(document.querySelector('#editor'), {
      toolbar: [
        'heading', '|',
        'bold', 'italic', 'underline', 'link',
        'bulletedList', 'numberedList',
        'blockQuote', 'insertTable', 'imageUpload', 'mediaEmbed',
        'undo', 'redo', 'codeBlock'
      ],
      // Optional: configure simple image upload - change uploadUrl to your endpoint
      simpleUpload: {
        uploadUrl: '/api/uploads',
        headers: (function () {
          const h = {};
          if (csrfHeader && csrfToken) {
            h[csrfHeader] = csrfToken;
          }
          return h;
        })()
      }
    })
    .then(editor => {
      editorInstance = editor;
      window._journalEditor = editor; // debug access
      console.log('CKEditor initialized.');
    })
    .catch(error => {
      console.error('CKEditor init error:', error);
    });

  // On form submit copy editor data into textarea (your textarea has id="editor" and name="content")
  // If you use <textarea id="editor"> bound to content, ClassicEditor updates that automatically in many builds,
  // but to be safe ensure data is sent in form:
  const form = document.getElementById('journalForm');
  if (form) {
    form.addEventListener('submit', function () {
      if (editorInstance) {
        // find the textarea (id=editor)
        const textarea = document.querySelector('#editor');
        if (textarea) {
          textarea.value = editorInstance.getData();
        }
      }
    });
  }
});
