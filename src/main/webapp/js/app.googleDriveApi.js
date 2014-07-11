var app = app || {};
(function() {
	app.googleDriveApi = {
        fileList: function(param){
            param = param||{};
            param.method = "Get";
            return app.getJsonData(contextPath + "/googleDrive/filelist", param);
        },
        searchFile: function(param){
        	param = param||{};
            param.method = "Get";
            return app.getJsonData(contextPath + "/googleDrive/search", param);
        },
        trashFile: function(param){
        	param = param || {};
            param.method = "Get";
            return app.getJsonData(contextPath + "/googleDrive/trashFile", param);
        },
        untrashFile: function(param){
        	param = param || {};
            param.method = "Get";
            return app.getJsonData(contextPath + "/googleDrive/untrashFile", param);
        },
        deleteFile: function(param){
        	param = param || {};
            param.method = "Get";
            return app.getJsonData(contextPath + "/googleDrive/deleteFile", param);
        },
        trashList: function(param){
        	param = param || {};
            param.method = "Get";
            return app.getJsonData(contextPath + "/googleDrive/trashlist", param);
        }
	};
})();
