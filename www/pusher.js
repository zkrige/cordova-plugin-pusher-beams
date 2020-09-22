var exec = require('cordova/exec');

var PusherBeams = {
    registerUserId: function (tokenUrl, userId, authToken, successCallback, errorCallback) {
        exec(successCallback, errorCallback, "PusherBeams", "registerUserId", [tokenUrl, userId, authToken]);
    }
};

module.exports = PusherBeams;
