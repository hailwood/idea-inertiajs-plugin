# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]
- PS-2021.3 support

## [0.1.2]
### Fixed
- Completion results on Windows no longer show full path [#11](https://github.com/hailwood/idea-inertiajs-plugin/issues/11)

## [0.1.1]
### Added
- Notification when Inertia root does not exist
- Route::inertia support [#6](https://github.com/hailwood/idea-inertiajs-plugin/issues/6)
### Changed
- Inertia project detection now looks for `@inertiajs/inertia` in package.json or `inertiajs/inertia-laravel` in composer.json 

## [0.0.1]
### Added
- Go to reference support on Inertia::render and inertia() calls
- Go to usages line marker on first line of Inertia pages
- Settings page to set where Inertia pages are stored
