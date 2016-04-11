# wildfly-okta
SAML Service Provider on base of Wildfly/Picketlink authenticates users through Okta Identity Provider

## Prerequisites

Download and unzip ngrok
https://ngrok.com/download

## Infrastructure

1. Install & run local instance of WildFly 

2. Start tunnel to the local WildFly instance
    
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

## Run sp-redirect sample
1. Install "sp" security domain to WildFly
    
    ```
    $ mvn install -P conf -Dservice.url=http://xxxxxxxx.ngrok.io/hello -Didp.url=http://idp.oktadev.com
    ```
2. Build and install sample application
    
    ```
    $ mvn install wildfly:deploy
    ```
3. Open http://localhost:8080/hello in browser
4. You will be redirected to Okta Development IDP
5. Put url service url into "Issuer", "SP ACS URL" and "SP Audience URI" fields
6. Click "Sign In"

## Run sp-post sample
1. Install "sp" security domain to WildFly
    
    ```
    $ mvn install -P conf -Dservice.url=http://xxxxxxxx.ngrok.io/hola -Didp.url=http://idp.oktadev.com
    ```
2. Build and install sample application
    
    ```
    $ mvn install wildfly:deploy
    ```
3. Open http://localhost:8080/halo in browser
4. You will be redirected to Okta Development IDP
5. Put url service url into "Issuer", "SP ACS URL" and "SP Audience URI" fields
6. Click "Sign In"