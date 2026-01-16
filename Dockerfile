FROM theasp/clojurescript-nodejs:shadow-cljs-alpine as build
WORKDIR /app
COPY ["package.json", "package-lock.json", "shadow-cljs.edn", "/app/"]
RUN shadow-cljs npm-deps && npm install --save-dev shadow-cljs
COPY ./ /app/
RUN shadow-cljs release app

FROM node:25.3.0-alpine3.23
WORKDIR /app
ENV NODE_ENV production
EXPOSE 4000
COPY --from=build /app/ /app/
CMD npm start
