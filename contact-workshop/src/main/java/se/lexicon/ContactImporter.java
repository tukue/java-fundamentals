package se.lexicon;

import java.util.List;

public interface ContactImporter {

    ImportOutcome parse(List<String> lines);
}
