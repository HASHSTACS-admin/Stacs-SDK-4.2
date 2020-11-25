import io.settlity.drs.client.DrsClient;
import io.settlity.drs.client.DrsClientConfig;
import io.settlity.drs.client.DrsClientFactory;
import io.settlity.drs.client.vo.CommonResponse;
import io.settlity.drs.client.vo.biz.AddressCreateRequest;
import io.settlity.drs.client.vo.biz.IssueMessageResponse;
import io.settlity.drs.client.vo.biz.smt.SmtMessage;
import io.settlity.drs.client.vo.biz.smt.SmtMessageHeader;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DrsClientExample {

    public static void main(String[] args) {

        String drsHost = "http://10.200.174.112:9716";
        String identifierId = "NICNNNNNNNNnnnnn";
        String appPrivateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCqorla7Vy+DBZRRetlwzxWl3sNdk/EkGHZkWcpw6fjwuATMOH0aJRPyTtTaeEFfjGXEXPn/OYEZhwig04yTIW0Vkx/6zfddrMzcmECP5Dm4ySxmJNXHJOXAT1vsln5q2Z2DTlU/Jfr7EBI5gZbpXBDxGWvvEBZ5F06Kc9QxKXvtJeW7mhhinr4VGesL0eA7b6eGEGxYrEZLoXoutY8Kwc9aIhOQIFiHk2ta9zajBngL4MBkxrfmtXyuccs0JUaY/xEN3yRDHIPCWo7a1ujIWJS0VuzeIPL+qfyNQtCUS4RPH7NzfvlLeKebwk8vs14v6vm7U/m33JXrdtURHaZHYZ3AgMBAAECggEANvk58jtql6fZQNHf5uchd1xSPqIRTOifPbMEo4Xvfwd3nXcwNMfEkHfqbxM7dcOUQnFTADiP+nbTNaNL/BR/NvxepQ+o7eaiET8WVd0Y1+nyFm1rerMwMYQqwOOa7T71Qa88+05tu4pfo5zIC5qfMpYBlL53lP5nsqdprUHNnA3xiLZMJfjrladSntCo1p1rLKz8REffDOY2ncszYUofpnk74ID8qCGClwBVWUYIEavE7xLPNW62AH0yl30FMzCk6oylmadz8HURqjfIzRj1K9sYzpyLf0vXgH/qRqwVsMynFr2tSxJoQdjBy0dZzbVYJiK1B9HNM19bW3Fc+wY5UQKBgQDg2FzMn6dDGLIG6fuiKhBcn+vfKK3fTGYTJhhDMvtMwPXPDiRCFwf+c7d82i36kMbVZpeGh3EsfvXQxwQb8ZnaUB8xKTIUv/CYSAFGUzRYzybQyhzZ5uQ6VQc/gA8YJTUhE/6RX/+tFH3bIrHfp7c6izNb1s70lsWAJZc0TxSltQKBgQDCR3VMjkvcAp1zuErhgrqKAMpVYCKzhwbSyZjJo69RI85hvOMHbv+UQcrsMQTw+S4oP4qr1ssrUSWskw16MxK72XNAfWXZ+czF1bc1POSkxdbcwat3gbjQ713r4c1YVIPQoF0IG4Fw37MbL9Y1mxzi/zRyXvxgTPcEpq2LeAuW+wKBgBizrH5Oi/GzJNvdmOeX93Q1nZRGw9NkcVJpPQEk+LbV/CtjQliAb0BlNiNhVOvSPAX132/IMYbR6++j7a+kO2LgqoycGgRtkeEpB/rR6U5Yc8s8aS8HTsuZy9IOH5pnfA7GQ5F1GoHJXopUpXYM1vR3lozgbJwydEA/4Py+M2aBAoGAbzdTCfFHsfn5Qr6Ud0ZMHSZYrckr9FPu7wojIgNHII773W2xFZdQTgHf9ZgWORCcgMPnUXnTssdwZruD8pl8NvdDh/6BKOhcdkBi8eIbDcyHtLZVv0MOluUAgkEwOUmgb/TtbO+6CPC2anDXErbqMXRhBLwlg8drfUkhff7Ju7MCgYAaFYRvuygFp/XI+M3PO5bDJVIuO9POSzP8q9XQB1oFKptleohRs1iFjnSS1xPo9cnZqyDmVP1figEjlKrtXgqsaQ694AUlvUWcYSwMWKc0BRa9bTkK2sWwfR3jBND2qBj8kZ3BJzYE2f2UC4PaFWbFdz+Rq27gfpjE2ETC91sbng==";
        String drsPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAt7OI4T1lKU3z2PPsSDZk8t+WCfzkmlNSRJTX0zDkVRGLuP5yHkuslfOrhf645oT8vguX5a13/CjqtUK9VvIAfhVzWARDD+D5ptLSE5zYBtaqHAqi0Gc8M1ez5i8NIBJC0WU2nnEy3a0JUhNvwrK1WG4ENhDuHjHYlb3O7zPeZ3Nnj+o42h1SZ4NRX9zsSSIk0vZLPmOh7cekoazOnrNXXIXa7pDQ4LJ7vN+oC+tsyxgYz6EOS65yghNGS0EO3Bxn8TLDxbK4yfvMME3HqSfYJGmTM5EkGKgWpwPB3L0KJhDtk0ukntIhXo2kqqIqGpzOhqqJaAsiWjHWoUrCU7JmpQIDAQAB";

        // create drs client instance
        DrsClientConfig config = new DrsClientConfig(drsHost, identifierId, appPrivateKey, drsPublicKey);
        DrsClient drsClient = DrsClientFactory.create(config);

        // example api: /smt/address/create
        AddressCreateRequest request = new AddressCreateRequest();
        request.setIdentifierId(identifierId);
        request.setUuid(uuid());

        CommonResponse<String> addressCreateResponse = drsClient.createAddress(request);

        assertTrue(addressCreateResponse.success());
        assertTrue(StringUtils.isNotBlank(addressCreateResponse.getData()));

        // example api: /endpoint
        Transfer transfer = new Transfer();
        transfer.setAssetId("TESTJX02");
        transfer.setTargetAddress("056d1a9c5228d6935963b7e9c3eb867d1aaf04ba");
        transfer.setQuantity(BigDecimal.ONE);

        SmtMessageHeader smtMessageHeader = new SmtMessageHeader();
        smtMessageHeader.setIdentifierId("BICASDASFASASASD");
        smtMessageHeader.setUuid("f7b1f202a9d84b1895e59328985cd9b0");
        smtMessageHeader.setMessageSenderAddress("23c6792d418f5c60272f01fbbfff325b8a195720");
        smtMessageHeader.setSmtCode("smtt-bond-transfer-transfer-1-v1");

        SmtMessage<Transfer> message = new SmtMessage<>();
        message.setHeader(smtMessageHeader);
        message.setBody(transfer);

        CommonResponse<IssueMessageResponse> issueMessageResponse = drsClient.issueMessage(message);
        if (issueMessageResponse.success()) {
            System.out.println("Invoke success");
        } else {
            System.out.println("Invoke fail, code: " + issueMessageResponse.getCode()
                    + ", message: " + issueMessageResponse.getMessage());
        }
    }

    static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    static class Transfer {

        private String assetId;
        private String targetAddress;
        private BigDecimal quantity;

        public String getAssetId() {
            return assetId;
        }
        public void setAssetId(String assetId) {
            this.assetId = assetId;
        }
        public String getTargetAddress() {
            return targetAddress;
        }
        public void setTargetAddress(String targetAddress) {
            this.targetAddress = targetAddress;
        }
        public BigDecimal getQuantity() {
            return quantity;
        }
        public void setQuantity(BigDecimal quantity) {
            this.quantity = quantity;
        }
    }
}
