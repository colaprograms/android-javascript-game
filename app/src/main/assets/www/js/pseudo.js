"use strict";

/* Make a pseudo-android class if we are running in the browser */

(function() {
  var make_pseudo = function() {
    window.android = {
      test: function() { return 99; },
      filesdirectory: function() { return "nothin" }
    }
  }

  if(window.android === undefined) {
    make_pseudo();
  }
})();
