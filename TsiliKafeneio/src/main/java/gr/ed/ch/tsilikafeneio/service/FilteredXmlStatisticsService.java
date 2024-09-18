package gr.ed.ch.tsilikafeneio.service;

import gr.ed.ch.tsilikafeneio.model.Statistics;

import java.io.File;

public interface FilteredXmlStatisticsService {
    Statistics generateFilteredXmlStatistics(File filteredXmlFile) throws Exception;
}
