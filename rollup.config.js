import { readFileSync } from 'fs'
import { cwd } from 'process'
import { join } from 'path'
import { nodeResolve } from '@rollup/plugin-node-resolve'
import typescript from '@rollup/plugin-typescript'
import terser from '@rollup/plugin-terser'

const createConfig = (options = {}) => {
  const { input = 'webview-src/index.ts', external = [/^@tauri-apps\/api/], additionalConfigs = [] } = options

  const pkg = JSON.parse(readFileSync(join(cwd(), 'package.json'), 'utf8'))

  const pluginJsName = pkg.name.replace('@cakioe/tauri-plugin-', '').replace(/-./g, x => x[1].toUpperCase())

  const iifeVarName = `__TAURI_PLUGIN_${pluginJsName.toUpperCase()}__`

  return [
    {
      input,
      output: [
        {
          file: pkg.exports.import,
          format: 'esm'
        },
        {
          file: pkg.exports.require,
          format: 'cjs'
        }
      ],
      plugins: [
        typescript({
          declaration: true,
          declarationDir: `./${pkg.exports.import.split('/')[0]}`
        })
      ],
      external: [...external, ...Object.keys(pkg.dependencies || {}), ...Object.keys(pkg.peerDependencies || {})],
      onwarn: warning => {
        throw Object.assign(new Error(), warning)
      }
    },

    {
      input,
      output: {
        format: 'iife',
        name: iifeVarName,
        banner: 'if (\'__TAURI__\' in window) {',
        footer: `Object.defineProperty(window.__TAURI__, '${pluginJsName}', { value: ${iifeVarName} }) }`,
        file: 'api-iife.js'
      },
      plugins: [typescript(), terser(), nodeResolve()],
      onwarn: warning => {
        throw Object.assign(new Error(), warning)
      }
    },

    ...(Array.isArray(additionalConfigs) ? additionalConfigs : [additionalConfigs])
  ]
}

// const config = [{
//   input: 'webview-src/index.ts',
//   plugins: [
//     typescript({
//       include: ['webview-src/**/*.ts', 'webview-src/**/*.tsx'],
//       tsconfig: 'tsconfig.json'
//     }),
//     terser()
//   ],
//   output: [{
//     file: 'webview-dist/index.mjs',
//     format: 'es',
//     sourcemap: true
//   }, {
//     file: 'webview-dist/index.cjs',
//     format: 'cjs',
//     sourcemap: true
//   }]
// }, {
//   input: './webview-src/index.d.ts',
//   output: [{ file: 'webview-dist/index.d.ts', format: 'es' }],
//   plugins: [dts()]
// }]

export default createConfig
