import * as TypeGraphQL from "type-graphql";

export enum PostScalarFieldEnum {
  authorId = "authorId",
  content = "content",
  id = "id",
  published = "published",
  title = "title"
}
TypeGraphQL.registerEnumType(PostScalarFieldEnum, {
  name: "PostScalarFieldEnum",
  description: undefined,
});
