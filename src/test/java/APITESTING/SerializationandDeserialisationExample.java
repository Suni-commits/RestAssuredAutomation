package APITESTING;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;

public class SerializationandDeserialisationExample {
// pojo(java Object)----Serialisation--JSON Object, JSON object---->deserialization--->POJO Object

    @Test
    //Converting POJO Object to JSON Object
    void PojotoJson() throws JsonProcessingException {
        Student stuObj=new Student();

        stuObj.setFirstName("Jacksonss");
        stuObj.setLastName("Robort");
        stuObj.setAge(28);
        stuObj.setEmail("tessst@gmai.com");
        String[] coursesArr={"C","C++","JAVA"};
        stuObj.setCourses(coursesArr);

        ObjectMapper objmapper=new ObjectMapper();
        String jsondata= objmapper.writerWithDefaultPrettyPrinter().writeValueAsString(stuObj);

        System.out.println("Converting POJO object to JSON OBJECT: "+jsondata);

    }

    @Test
    // Converting JSON Object to POJo Object
    void JSONtoPOJO() throws JsonProcessingException {
        String jsondata="{\n" +
                "  \"firstName\" : \"Jacksonss\",\n" +
                "  \"lastName\" : \"Robort\",\n" +
                "  \"age\" : 28,\n" +
                "  \"email\" : \"tessst@gmai.com\",\n" +
                "  \"courses\" : [ \"C\", \"C++\", \"JAVA\" ]\n" +
                "}";

        ObjectMapper objmap=new ObjectMapper();
           Student stu= objmap.readValue(jsondata, Student.class);
           System.out.println("Firstname of the Student : "+stu.getFirstName());
                System.out.println("Lastname of  the student : "+stu.getLastName());
                            System.out.println("Age of the Student : "+stu.getAge());
                                    System.out.println("Age of the Student : "+stu.getEmail());
                                            System.out.println("First course of the student : "+stu.getCourses()[0]);
        System.out.println("Second course of the student: "+stu.getCourses()[1]);
        System.out.println("Third course of the student: "+stu.getCourses()[2]);


    }

}
