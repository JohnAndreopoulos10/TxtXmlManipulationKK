package gr.ed.ch.tsilikafeneio.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Statistics {
    private int lineCount;
    private int paragraphCount;
    private int wordCount;
    private int distinctWordCount;
    private int chapterCount;
    private LocalDateTime creationTime;
    private String authorName;
    private String applicationClassName;
}
