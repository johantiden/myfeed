package se.johantiden.myfeed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.johantiden.myfeed.persistence.model.Document;
import se.johantiden.myfeed.persistence.model.User;
import se.johantiden.myfeed.persistence.model.UserDocument;
import se.johantiden.myfeed.service.DocumentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@EnableAutoConfiguration
public class IndexController {

    @Autowired
    private DocumentService documentService;

    @RequestMapping("/rest/index")
    List<Document> index() {

        List<Document> outputs = getRealOutput();


        for (Document document : outputs) {
            System.out.println(document);
        }


        return outputs;
    }

    private List<Document> getRealOutput() {

        User johan = User.johan();

        List<UserDocument> userDocuments = documentService.getUnreadDocumentsFor(johan);


        return userDocuments.stream().map(UserDocument::getDocument).collect(Collectors.toList());
    }
}
