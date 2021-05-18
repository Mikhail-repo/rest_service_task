"use strict"

 window.addEventListener("load", PagePostConstruct, false);

 var topicNameInput;
 var topicDescriptionInput;
 var createTopicButton;

  function PagePostConstruct() {

    var select = document.getElementById("editQuestionTypeInput");
    if(select != null) {
        var selValue = select.getAttribute("name");
        var options = select.childNodes;
        for(var i = 0; i < options.length; i++) {
          if(options[i].value === selValue)
            options[i].selected = true;
        }
    }
    createTopicButton = document.getElementById("createTopicButton");
    topicNameInput = document.getElementById("topicNameInput");
    topicDescriptionInput = document.getElementById("topicDescriptionInput");
      //Register event's listeners.  
      SetEventListeners();
  }

  function SetEventListeners() {
    createTopicButton.onclick = createTopicButtonOnClickHandler;
  }

  function successResponseFunc(data, textStatus, xhr){
    location.reload();
  }

  function createTopicButtonOnClickHandler() {
    var type = "POST";
    var url = "/RestServiceTask/api/createtopic";
    var name = topicNameInput.value;
    var description = topicDescriptionInput.value;
    var data = "{\"name\": \"" + name + "\", \"startDate\":\"2021-05-12T16:24:52Z\", \"endDate\":\"2021-05-13T16:24:52Z\", \"description\":\"" + description + "\"}";

    sendAjaxRequest(type, url, data, successResponseFunc, null);
  }

  function deleteTopicButtonOnclickHandler(topicId) {
    var type = "DELETE";
    var url = "/RestServiceTask/api/deletetopic/" + topicId;

    sendAjaxRequest(type, url, null, successResponseFunc, null);
  }

  function createQuestionButtonOnclickHandler(pollId) {
     var type = "POST";
     var url = "/RestServiceTask/api/createquestion";
     var questionType = document.getElementById("questionTypeInput").value;
     var questionText = document.getElementById("questionTextInput").value;
     var data = "{\"questionType\": \"" + questionType + "\",  \"questionText\":\"" + questionText + "\",  \"pollId\":\"" + pollId + "\"}";

     sendAjaxRequest(type, url, data, successResponseFunc, pollId);
  }

  function deleteQuestionButtonOnclickHandler(topicId, questionId) {
     var type = "DELETE";
     var url = "/RestServiceTask/api/deletequestion/" + questionId;

     sendAjaxRequest(type, url, null, successResponseFunc, topicId);
  }

  function updateQuestionButtonOnclickHandler(pollId, questionId) {
       var type = "PUT";
       var url = "/RestServiceTask/api/updatequestion/" + questionId;
       var questionType = document.getElementById("editQuestionTypeInput").value;
       var questionText = document.getElementById("questionTextInput").value;
       var data = "{\"questionType\": \"" + questionType + "\",  \"questionText\":\"" + questionText + "\"}";

       sendAjaxRequest(type, url, data, successResponseFunc, pollId);
  }

  //Send AJAX-request.
  function sendAjaxRequest(requestType, url, data, callbackFunc, pollId) {

	      jQuery.ajax({
	                   url: url,
	                   type: requestType, 
	                   headers: {
	                     'PollId': pollId,
	                     'Host': 'localhost:8080',
	                     'Content-Type': 'application/json',
	                   },
	                   data: data,
	                   success: function(data, textStatus, xhr) {
	                	  callbackFunc(data, textStatus, xhr);
	                  }    
	      }); 
  }