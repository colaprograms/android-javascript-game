"use strict";

$(function() {
  var set_where = function(s) {
    $("#location").html(s)
  }
  var loop = function() {
    var answer = android.cmd("location", "get", "");
    if(answer.substring(0, 6) == "done: ") {
      var where = answer.substring(6)
      if(where != "null") {
        set_where(where)
        return;
      }
    }
    setTimeout(loop, 2000)
  }
  loop()
});
