import com.ruoyi.RuoYiApplication;
import com.ulpay.testplatform.domain.CaseRunData;
import com.ulpay.testplatform.service.ITestCaseService;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RuoYiApplication.class)
public class CaseTest {

    @Autowired
    ITestCaseService iTestCaseService;

    @Test
    public void caseRunTest() throws Exception {
//        CaseRunData caseRunData = new CaseRunData();
//        caseRunData.setCaseId(8L);
//        caseRunData.setEnvConf("10.63.11.21:11120");
//        iTestCaseService.runCase(caseRunData);
    }
}
