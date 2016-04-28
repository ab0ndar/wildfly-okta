# wildfly-okta
Simple example demonstrates how to integrate [Okta](https://www.okta.com/) as [Identity Provider](https://en.wikipedia.org/wiki/Identity_provider) into [Wildfly](http://wildfly.org/) application server (aka open source JBOSS) which serves as Service Provider by means of [PicketLink](http://picketlink.org/) framework. 
There are two SAML 2.0 bindings shown: [REDIRECT](https://en.wikipedia.org/wiki/SAML_2.0#HTTP_Redirect_Binding) in the [sp-redirect](./sp-redirect) module and [POST](https://en.wikipedia.org/wiki/SAML_2.0#HTTP_POST_Binding) in the [sp-post](./sp-post) correspondingly. 
This example extends couple of [PicketLink](http://picketlink.org/) classes (see [sp-common classes](./sp-common/src/main/java/org/ab0ndar/)) in order to overcome discrepancies in SAML 2.0 implementation between PicketLink and Okta.     

## Prerequisites

First, we need to open access to our local SP from the Internet, so Okta IDP can send HTTP requests thereto. 
Instructions below assume you have established [ngrok](https://ngrok.com/) tunnel to the Wildfly instance running on your localhost. 
So, here is a link to [ngrok](https://ngrok.com/download)

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
0. If you ran another sample before, clean up the WildFly configuration first:
  
    ```
    mvn3 install -P clean
    ```
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
0. If you ran another sample before, clean up the WildFly configuration first:
  
    ```
    mvn3 install -P clean
    ```
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