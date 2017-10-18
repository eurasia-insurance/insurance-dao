package tech.lapsa.insurance.dao.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

public final class Predictates {

    private Predictates() {
    }

    public static enum MatchMode {
	AND, OR
    };

    public static Optional<Predicate> textMatches(CriteriaBuilder cb, Expression<String> expression, String matchesTo) {
	return textMatches(MatchMode.AND, cb, expression, matchesTo);
    }

    public static Optional<Predicate> textMatches(MatchMode matchMode, CriteriaBuilder cb,
	    Expression<String> expression,
	    String matchesTo) {
	if (matchesTo == null || matchesTo.trim().isEmpty())
	    return Optional.empty();
	List<Predicate> words = new ArrayList<>();
	for (String verb : matchesTo.split("\\s+")) {
	    String pattern = '%' + verb.replaceAll("%", "") + '%';
	    words.add(cb.like(expression, pattern));
	}
	Predicate[] list = words.toArray(new Predicate[0]);
	switch (matchMode) {
	case OR:
	    return Optional.of(cb.or(list));
	case AND:
	default:
	    return Optional.of(cb.and(list));
	}
    }
}
