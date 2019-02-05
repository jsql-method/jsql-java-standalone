package it.jsql.standalone.controller;

import it.jsql.connector.controller.JSQLController;
import it.jsql.connector.service.IJSQLService;
import it.jsql.connector.service.JSQLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import it.jsql.DatabaseApplication;


@CrossOrigin
@RestController
public class AppController extends JSQLController {

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public IJSQLService getJsqlService() {
        return new JSQLService(applicationContext, DatabaseApplication.API_KEY, DatabaseApplication.MEMBER_KEY);
    }

}
