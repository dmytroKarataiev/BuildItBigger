/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.example.karataev.myapplication.backend;

import com.example.JokesProvider;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;

/** An endpoint class we are exposing */
@Api(
  name = "myApi",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "backend.myapplication.karataev.example.com",
    ownerName = "backend.myapplication.karataev.example.com",
    packagePath=""
  )
)
public class MyEndpoint {

    /** A simple endpoint method that takes a name and says Hi back */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String name) {
        MyBean response = new MyBean();

        JokesProvider jokesProvider = new JokesProvider();

        response.setData("Hi, " + jokesProvider.getJoke());

        return response;
    }

    @ApiMethod(name = "getJoke")
    public MyBean getJoke() {
        MyBean response = new MyBean();

        response.setData(new JokesProvider().getJoke());

        return response;

    }

    @ApiMethod(name = "getFreeJoke")
    public MyBean freeJoke() {
        MyBean response = new MyBean();

        response.setData(new JokesProvider().getFreeJoke());

        return response;

    }

}
