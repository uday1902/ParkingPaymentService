package devcraft.parking;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions( //
        features = "classpath:features", //
        glue = { //
                "devcraft.parking", //
        })
public class CucumberTests {

}