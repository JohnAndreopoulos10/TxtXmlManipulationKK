package gr.ed.ch.tsilikafeneio.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paragraph {
    private int paragraphNumber;              
    private List<String> lines;              
}