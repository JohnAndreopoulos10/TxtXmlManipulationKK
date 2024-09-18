package gr.ed.ch.tsilikafeneio.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chapter {
    private int chapterNumber;           
    private List<Paragraph> paragraphs;  
}