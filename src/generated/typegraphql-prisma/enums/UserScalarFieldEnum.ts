import * as TypeGraphQL from "type-graphql";

export enum UserScalarFieldEnum {
  email = "email",
  id = "id",
  name = "name"
}
TypeGraphQL.registerEnumType(UserScalarFieldEnum, {
  name: "UserScalarFieldEnum",
  description: undefined,
});
