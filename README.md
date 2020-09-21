# Cordova Pusher-Beams plugin

A cordova plugin that registers a user id with Pusher-Beams

You still need to do manual Pusher-Beams configuration and setup in your native app. This is a bridge to register with beams once you have a userid / authtoken in your JS app

## Using

Create a new Cordova Project

    $ cordova create beams com.example.beamsapp Beams
    
Install the plugin

    $ cd hello
    $ cordova plugin add https://github.com/zkrige/cordova-plugin-pusher-beams.git
    

Edit `www/js/index.js` and add the following code when you have a userid and authtoken

```js
    this.Storage.get('access_token').then(token => {
      this.Storage.get('userid').then(id => {
        PusherBeams.registerUserId(id, token);
      });
    });
```

Install iOS or Android platform

    cordova platform add ios
    cordova platform add android
    
Run the code

    cordova run 

## More Info

For more information on setting up Cordova see [the documentation](http://cordova.apache.org/docs/en/latest/guide/cli/index.html)

For more info on plugins see the [Plugin Development Guide](http://cordova.apache.org/docs/en/latest/guide/hybrid/plugins/index.html)
