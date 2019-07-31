"use strict";
$(function() {
  $(".test").html( android.cmd("location", "start", "") )
  /*
  $(".test").html( android.filesdirectory() );
  if(navigator.geolocation !== undefined) {
    navigator.geolocation.getCurrentPosition(function(z) {
      $("#location").html(z);
    });
  }
  */
});
