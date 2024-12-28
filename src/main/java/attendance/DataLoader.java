package attendance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    public List<String> loadData(String filePath) {
        List<String> data = new ArrayList<>();
        readFile(filePath, data);
        return data;
    }

    public void readFile(String filePath, List<String> data) {
        try {
            File file = new File(filePath);
            BufferedReader br = new BufferedReader(new FileReader(file));
            skipHeader(br);
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("파일을 로드하는 중 문제가 발생했습니다.");
        }
    }

    public void skipHeader(BufferedReader br) throws IOException {
        br.readLine();
    }
}
