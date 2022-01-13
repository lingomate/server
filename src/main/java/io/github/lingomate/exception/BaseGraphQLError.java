package io.github.lingomate.exception;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import graphql.schema.DataFetchingEnvironment;
import io.github.wickedev.graphql.exceptions.ErrorExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static graphql.Assert.assertNotNull;

public class BaseGraphQLError extends Exception implements GraphQLError, ErrorClassification {
    private final List<Object> path;
    private final List<SourceLocation> locations;
    private final Map<String, Object> extensions;

    BaseGraphQLError(String code, String message, DataFetchingEnvironment env) {
        super(message);
        var path = env.getExecutionStepInfo().getPath();
        var sourceLocation = env.getField().getSourceLocation();
        this.path = assertNotNull(path).toList();
        this.locations = Collections.singletonList(sourceLocation);
        this.extensions = Collections.singletonMap(code, new ErrorExtension(this));
    }

    @Override
    public Map<String, Object> getExtensions() {
        return extensions;
    }

    @Override
    public List<SourceLocation> getLocations() {
        return locations;
    }

    @Override
    public ErrorClassification getErrorType() {
        return this;
    }

    @Override
    public List<Object> getPath() {
        return path;
    }
}
