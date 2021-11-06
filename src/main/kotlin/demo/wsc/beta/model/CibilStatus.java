package demo.wsc.beta.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NonNull
public class CibilStatus {
    public int score;
    public String scoreRatio;
    public String parameter1;
    public String parameter2;
    public String parameter3;
    public String parameter4;
    public List<String> eligibleLoans;
}
