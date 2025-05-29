package io.dimitris.markingmate.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import io.dimitris.markingmate.Answer;

public class AnswerSearcher {

	private static final String LUCENE_FEEDBACK_FIELD = "feedback";
	private static final String LUCENE_URI_FRAGMENT_FIELD = "uriFragment";
	protected static final int SEARCH_LIMIT = 10;

	public Collection<Answer> searchRelatedAnswers(Answer original, String searchQuery) {
		try (ByteBuffersDirectory directory = new ByteBuffersDirectory()) {
			Analyzer analyzer = new EnglishAnalyzer();
			index(directory, original, analyzer);
			return search(original.eResource(), searchQuery, analyzer, directory);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		
		return Collections.emptyList();
	}

	protected void index(ByteBuffersDirectory directory, Answer answer, Analyzer analyzer) throws IOException {
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		try (IndexWriter writer = new IndexWriter(directory, config)) {
			for (Answer other : answer.getQuestion().getAnswers()) {
				if (other == answer) continue;
				addDocument(writer, other);
			}
		}
	}

	protected void addDocument(IndexWriter writer, Answer relatedAnswer) throws IOException {
		Document doc = new Document();
		doc.add(new TextField(LUCENE_FEEDBACK_FIELD, relatedAnswer.getFeedback(), Field.Store.YES));
		doc.add(new StringField(LUCENE_URI_FRAGMENT_FIELD, relatedAnswer.eResource().getURIFragment(relatedAnswer), Field.Store.YES));
		writer.addDocument(doc);
	}

	protected Collection<Answer> search(Resource resource, String searchQuery, Analyzer analyzer, ByteBuffersDirectory directory) throws IOException, ParseException {
		searchQuery = searchQuery.trim();

		List<Answer> answers = new ArrayList<>();
		try (IndexReader reader = DirectoryReader.open(directory)) {
			IndexSearcher searcher = new IndexSearcher(reader);
			QueryParser parser = new QueryParser(LUCENE_FEEDBACK_FIELD, analyzer);
			Query query = parser.parse(searchQuery);

			ScoreDoc[] hits = searcher.search(query, SEARCH_LIMIT).scoreDocs;
			for (ScoreDoc hit : hits) {
				Document hitDoc = searcher.storedFields().document(hit.doc);
				String uriFragment = hitDoc.get(LUCENE_URI_FRAGMENT_FIELD);
				EObject eob = resource.getEObject(uriFragment);
				if (eob instanceof Answer answer) {
					answers.add(answer);
				}
			}
		}
		return answers;
	}

}
