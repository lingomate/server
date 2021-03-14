import 'reflect-metadata'
import { ApolloServer } from 'apollo-server'
import { context } from "./context";
import { buildSchema } from "type-graphql";
import { resolvers } from "./generated/typegraphql-prisma";

const PORT = process.env.PORT || 4000;

async function bootstrap() {
    // ... Building schema here
    const schema = await buildSchema({
        resolvers,
        validate: false,
    });

    // Create the GraphQL server
    const server = new ApolloServer({
        schema: schema,
        playground: true,
        context
    });

    // Start the server
    const { url } = await server.listen(PORT);
    console.log(`Server is running, GraphQL Playground available at ${url}`);
}

bootstrap().then()
