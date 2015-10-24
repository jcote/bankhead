
// # Globbing
// for performance reasons we're only matching one level down:
// 'test/spec/{,*/}*.js'
// use this if you want to recursively match all subfolders:
// 'test/spec/**/*.js'

module.exports = function (grunt) {
  require('load-grunt-tasks')(grunt);
  require('time-grunt')(grunt);

  // pass in something like:
  //   --target=/target/bankhead-web-main-1.0
  var target = grunt.option('target') || 'target/bankhead-web-main-1.0-SNAPSHOT';

  // configurable paths
  var yeomanConfig = {
    app: 'src/main/webapp/main',
    dist: target
  };

  try {
    yeomanConfig.app = require('./bower.json').appPath || yeomanConfig.app;
  } catch (e) {}

  grunt.initConfig({
    yeoman: yeomanConfig,
    watch: {
      jshint: {
        files: ['<%= yeoman.app %>/js/{,*/}*.js'],
        tasks: ['jshint', 'copy:distjs']
      },
      coffee: {
        files: ['<%= yeoman.app %>/js/{,*/}*.coffee'],
        tasks: ['coffee']
      },
      sass: {
        files: ['<%= yeoman.app %>/css/{,*/}*.scss'],
        tasks: ['compass']
      },
      imagemin: {
        files: ['<%= yeoman.app %>/image/{,*/}*.{png,jpg,jpeg}'],
        tasks: ['imagemin']
      },
      copycss: {
        files: ['<%= yeoman.app %>/css/{,*/}*.css'],
        tasks: ['copy:distcss']
      },
      copyhtml: {
        files: ['<%= yeoman.app %>/{,*/}*.html'],
        tasks: ['copy:disthtml']
      }
    },
    clean: {
      dist: {
        files: [{
          dot: true,
          src: [
            '.tmp',
            '<%= yeoman.dist %>/main/js/{,*/}*.js',
            '<%= yeoman.dist %>/main/css/{,*/}*.css',
            '<%= yeoman.dist %>/main/image{,*/}*.{png,jpg,jpeg}',
            '<%= yeoman.dist %>/main/{,*/}*.html'
          ]
        }]
      }
    },
    bower: {
      install: {
        options: {
          targetDir: '<%= yeoman.dist %>/lib',
          layout: 'byComponent',
          install: true,
          verbose: true,
          cleanTargetDir: true,
          cleanBowerDir: false,
          bowerOptions: {}
        }
      }
    },
    jshint: {
      all: [
        'Gruntfile.js',
        '<%= yeoman.app %>/js/{,*/}*.js'
      ]
    },
    coffee: {
      options: {
        sourceMap: true,
        sourceRoot: ''
      },
      dist: {
        files: [{
          expand: true,
          cwd: '<%= yeoman.app %>/js',
          src: '{,*/}*.coffee',
          dest: '<%= yeoman.dist %>/main/js',
          ext: '.js'
        }]
      }
    },
    compass: {
      dist: {
        options: {
          sassDir: '<%= yeoman.app %>/css',
          cssDir: '<%= yeoman.dist %>/main/css',
        }
      }
    },
    imagemin: {
      dist: {
        files: [{
          expand: true,
          cwd: '<%= yeoman.app %>/image',
          src: '{,*/}*.{png,jpg,jpeg}',
          dest: '<%= yeoman.dist %>/main/image'
        }]
      }
    },
    copy: {
      distjs: {
        files: [{
          expand: true,
          cwd: '<%= yeoman.app %>/js',
          src: '{,*/}*.js',
          dest: '<%= yeoman.dist %>/main/js'
        }]
      },
      distcss: {
        files: [{
          expand: true,
          cwd: '<%= yeoman.app %>/css',
          src: '{,*/}*.css',
          dest: '<%= yeoman.dist %>/main/css'
        }]
      },
      disthtml: {
        files: [{
          expand: true,
          cwd: '<%= yeoman.app %>',
          src: '{,*/}*.html',
          dest: '<%= yeoman.dist %>/main'
        }]
      }
    },
    concurrent: {
      dist: [
        'jshint',
        'coffee',
        'compass',
        'imagemin',
        'copy'
      ]
    }
  });

  grunt.registerTask('build', [
    'clean:dist',
    'bower:install',
    'concurrent:dist'
  ]);

  grunt.registerTask('default', [
    'build'
  ]);
};
