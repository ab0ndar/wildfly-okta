# wildfly-okta
SAML Service Provider on base of Wildfly/Picketlink authenticates users through Okta Identity Provider

## Prerequisites

Download and unzip ngrok
https://ngrok.com/download

## Install

1. Start tunnel to the local WildFly instance
    
    ```
    $ ./ngrok http 8080
    
        ngrok by @inconshreveable
    
        Tunnel Status                 online
        Version                       2.0.25/2.0.25
        Region                        United States (us)
        Web Interface                 http://127.0.0.1:4040
        Forwarding                    http://xxxxxxxx.ngrok.io -> localhost:8080
        Forwarding                    https://xxxxxxxx.ngrok.io -> localhost:8080
    ```
2. Install "sp" security domain to WildFly
    
    ```
    $ mvn install -P conf -Dservice.url=http://xxxxxxxx.ngrok.io/hello
    ```
3. Build and install sample application
    
    ```
    $ mvn install wildfly:deploy
    ```

## Run
1. Open http://localhost:8080/hello in browser
2. You will be redirected to Okta Development IDP
3. Put url service url into "Issuer", "SP ACS URL" and "SP Audience URI" fields
4. Click "Sign In"