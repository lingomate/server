package io.github.lingomate.exception

import graphql.schema.DataFetchingEnvironment

class UserAlreadyExistException(env: DataFetchingEnvironment): BaseGraphQLError("USER_ALREADY_EXIST_EXCEPTION", "user already exist exception", env)