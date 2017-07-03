# TOI - TypeScript

> Totally Optional Interface

## Development

### Get started

First of all, install latest [Node.js].

Open a terminal into this folder, than with [npm] do

```bash
npm install
```

### Test

Launch tests, as usual, with

```bash
npm test
```

### Debug

On server side you can enable [debug] output by setting the `DEBUG`
environment variable, for example

```bash
export DEBUG=toi:*
```

In the browser use `localStorage` to enable [debug] state

```javascript
localStorage.debug = 'toi:*'
```

### Lint

Launch

```bash
npm run tslint
```

See also tslint.json file which configures the linter.

### Build

Just run

```bash
npm run typescript
```

See also tsconfig.json file which configures the build process.

[npm]: https://www.npmjs.com/ "npm"
[Node.js]: https://nodejs.org "Node.js"
[debug]: https://www.npmjs.com/package/debug "small debugging utility"
