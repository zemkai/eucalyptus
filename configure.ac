#                                               -*- Autoconf -*-
# Process this file with autoconf to produce a configure script.
#

# Usage:
#   configure   [--with-axis2c=<dir>]
#               [--with-axis2=<dir>]
#               [--with-libvirt=<dir>]
#               [--extra-version=<version>]
#               [--enable-debug]
#               [--db-home=<dir>]
#               [--db-suffix=<version>]
#               [--db-old-home=<dir>]
#               [--db-old-suffix=<version>]

AC_PREREQ(2.61)
AC_INIT(Eucalyptus,
        esyscmd([cat VERSION | tr -d '\n']),
        euca-users@eucalyptus.com)
AC_CONFIG_SRCDIR([wsdl/eucalyptus_nc.wsdl])
AC_CONFIG_HEADER([util/eucalyptus-config.h])
AC_PREFIX_DEFAULT([/opt/eucalyptus/])
AC_USE_SYSTEM_EXTENSIONS
AC_CANONICAL_BUILD

# construct two version strings
EUCA_VERSION_MAJOR=`echo $PACKAGE_VERSION | cut -d. -f1`
EUCA_VERSION_MINOR=`echo $PACKAGE_VERSION | cut -d. -f2`
EUCA_VERSION_PATCH=`echo $PACKAGE_VERSION | cut -d. -f3`
EUCA_VERSION_HOTFIX=`echo $PACKAGE_VERSION | cut -d. -f4`
EUCA_VERSION="${EUCA_VERSION_MAJOR}.${EUCA_VERSION_MINOR}.${EUCA_VERSION_PATCH}"
EUCA_VERSION_FULL="$PACKAGE_VERSION"

# let's figure out where is the source tree
if test ${srcdir} = "." ; then
        TOP=`pwd`
else
        TOP=${srcdir}
fi

# variables we'll need later on
APACHE2_MODULE_DIR=""
AXIS2C_HOME="${AXIS2C_HOME}"
AXIS2_HOME="${AXIS2_HOME}"
AXIS2C_SERVICES=""
LIBVIRT_HOME="${LIBVIRT_HOME}"
ANT=""
JAVA=""
WSDL2C=""
java_min_version="1.8.0"
ant_min_version="1.6.5"

# these are for large files (>2GB)
LDFLAGS="`getconf LFS64_LDFLAGS` `getconf LFS_LDFLAGS` $LDFLAGS"
LIBS="`getconf LFS64_LIBS` `getconf LFS_LIBS` $LIBS"
CFLAGS="${CFLAGS} `getconf LFS64_CFLAGS` `getconf LFS_CFLAGS`"

# compile options
CFLAGS="${CFLAGS} -Wall -fPIC -DHAVE_CONFIG_H -std=gnu99"
INCLUDES="${INCLUDES} -I. -I.. -Igenerated -I${TOP}/storage -I${TOP}/node -I${TOP}/util -I${TOP}/net -I${TOP}/util/stats"

# Arguments checking
AC_ARG_ENABLE(bundled-jars,
        AS_HELP_STRING([--disable-bundled-jars] [do not build against or install bundled jars]),
        [USE_BUNDLED_JARS=$enableval],
        [USE_BUNDLED_JARS=yes])
AC_ARG_WITH(apache2-module-dir,
        [  --with-apache2-module-dir=<dir>      where Apache modules are installed],
        [APACHE2_MODULE_DIR="${withval}"])
AC_ARG_WITH(axis2,
        [  --with-axis2=<dir>                   where axis2 is installed],
        [AXIS2_HOME="${withval}"])
AC_ARG_WITH(axis2c,
        [  --with-axis2c=<dir>                  where axis2c is installed],
        [AXIS2C_HOME="${withval}"])
AC_ARG_WITH(axis2c-services,
        [  --with-axis2c-services=<dir>         where axis2c services are installed],
        [AXIS2C_SERVICES="${withval}"])
AC_ARG_WITH(libvirt,
        [  --with-libvirt=<dir>                 where libvirt is installed],
        [LIBVIRT_HOME="${withval}"])
AC_ARG_WITH(wsdl2c-sh,
        [  --with-wsdl2c-sh=<file>              where WSDL2C.sh is installed],
        [WSDL2C="${withval}"])
AC_ARG_WITH(db-home,
        [  --with-db-home=<dir>                 where postgresql is installed],
        [EUCA_DB_HOME="${withval}"])
AC_ARG_WITH(db-suffix,
        [  --with-db-suffix=<dir>               where postgresql is installed],
        [EUCA_DB_SUFFIX="${withval}"])
AC_ARG_WITH(db-old-home,
        [  --with-db-old-home=<dir>             where previous postgresql is installed],
        [EUCA_DB_OLD_HOME="${withval}"])
AC_ARG_WITH(db-old-suffix,
        [  --with-db-old-suffix=<dir>           where previous postgresql is installed],
        [EUCA_DB_OLD_SUFFIX="${withval}"])
AC_ARG_ENABLE(debug,
        [  --enable-debug                       include debugging info when compiling],
        [if test "${enableval}" != "no"; then
                DEBUGGING_ENABLED=yes
                CFLAGS="$CFLAGS -g -DDEBUG"
        fi])
AC_ARG_WITH(extra-version,
        [  --extra-version=<str>                string to append to versions to make logs and messages more precise],
        [EXTRA_VERSION="${withval}"])
AC_ARG_WITH(java-home,
        [  --with-java-home=<dir>               where the JRE is installed (same as JAVA_HOME)],
        [JAVA_HOME="${withval}"])

# If we didn't specify the services directory, let's use the default
if test -z "$AXIS2C_SERVICES" ; then
        AXIS2C_SERVICES="${AXIS2C_HOME}/services"
fi

# If we didn't specify the location of Apache modules, assume mod_axis2
# resides within AXIS2C_HOME
if test -z "$APACHE2_MODULE_DIR"; then
        APACHE2_MODULE_DIR="${AXIS2C_HOME}/lib"
fi

AM_PATH_PYTHON([2.5])
AC_MSG_CHECKING([whether $PYTHON version is < 3.0])
AM_PYTHON_CHECK_VERSION([$PYTHON], [3.0],
                        [AC_MSG_RESULT([no])
                         AC_MSG_ERROR([Python >= 3.0 is not supported])],
                        [AC_MSG_RESULT([yes])])

# Fix the paths for includes and libraries
if test -n "${AXIS2C_HOME}" ; then
        if test -d "${AXIS2C_HOME}"/include ; then
                for x in ${AXIS2C_HOME}/include/axis2-* ${AXIS2C_HOME}/include/rampart-*; do
                        INCLUDES="${INCLUDES} -I$x"
                done
        else
                for x in /usr/include/axis2-* /usr/include/rampart-*; do
                        INCLUDES="${INCLUDES} -I$x"
                done
        fi
        if test -d "${AXIS2C_HOME}"/lib ; then
                LDFLAGS="-L${AXIS2C_HOME}/lib ${LDFLAGS}"
        fi
        if test -d "${AXIS2C_HOME}"/modules/rampart ; then
                LDFLAGS="-L${AXIS2C_HOME}/modules/rampart ${LDFLAGS}"
        fi
fi
if test -n "${LIBVIRT_HOME}" ; then
        if test -d "${LIBVIRT_HOME}"/include ; then
                INCLUDES="${INCLUDES} -I${LIBVIRT_HOME}/include"
                LDFLAGS="-L${LIBVIRT_HOME}/lib ${LDFLAGS}"
        fi
fi

# Checks for programs.
AC_PROG_AWK
AC_PROG_CC
AC_PROG_INSTALL
AC_PROG_MAKE_SET
AC_PATH_PROG(RM, rm)
AC_LANG_C

if test -z "$WSDL2C"; then
        WSDL2C=`readlink -f devel/euca-WSDL2C.sh`
fi

# we need JAVA_HOME
if test -z "$JAVA_HOME" ; then
        AC_PATH_PROG([JAVAC], javac,, $PATH)
        if test -z "$JAVAC" ; then
                AC_MSG_ERROR([JAVA_HOME is not defined and javac was not found in PATH])
        else
                JAVA_HOME=$( readlink -f ${JAVAC} | rev | cut -d/ -f3- | rev )
        fi
fi

AC_PATH_PROGS([ANT], ant17 ant,,$ANT_HOME/bin:$PATH)
AC_PATH_PROG([JAVA], java,,$JAVA_HOME/bin)

# Check the version of java and ant
if test -z "$ANT" ; then
        AC_MSG_ERROR([Cannot find ant!])
fi
if test -z "$JAVA" ; then
        AC_MSG_ERROR([Cannot find java!])
fi
java_version=`$JAVA -version 2>&1 | head -n 1 | awk -F '"' '{print $2}'`
goodversion=`expr $java_version ">=" $java_min_version`
if test $goodversion -eq 0; then
        AC_MSG_ERROR([Eucalyptus needs at least JDK version $java_min_version])
fi

ant_version=`$ANT -version 2>&1 | grep "Ant.* version" | \
        sed -e 's/.*Ant.* version \([[0-9.]]*\).*/\1/'`
goodversion=`expr $ant_version ">=" $ant_min_version`
if test $goodversion -eq 0; then
        AC_MSG_ERROR([Eucalyptus needs at least ANT version $ant_min_version])
fi
# some version of ant picks up the wrong java
java_version=`JAVA_HOME=$JAVA_HOME $ANT -diagnostics 2>&1 | grep ^java.version | \
        sed -e 's/java.* \([[0-9.]]*\).*/\1/'`
goodversion=`expr $java_version ">=" $java_min_version`
if test $goodversion -eq 0; then
        AC_MSG_ERROR([ANT is using the wrong java (version less than $java_min_version)])
fi


# Checks for libraries.
AC_CHECK_LIB([axis2_axiom],[axiom_node_get_parent],,AC_MSG_ERROR([Cannot find axis2 libs!]))
AC_CHECK_LIB([axis2_engine],[axis2_msg_ctx_init],,AC_MSG_ERROR([Cannot find axis2 libs!]))
AC_CHECK_LIB([axis2_http_common],[axis2_simple_http_svr_conn_get_peer_ip],,AC_MSG_ERROR([Cannot find axis2 libs!]))
AC_CHECK_LIB([axis2_http_receiver],[axis2_http_server_create],,AC_MSG_ERROR([Cannot find axis2 libs!]))
AC_CHECK_LIB([axis2_http_sender],[axis2_http_transport_sender_init],,AC_MSG_ERROR([Cannot find axis2 libs!]))
AC_CHECK_LIB([guththila],[guththila_create_xml_stream_writer],,AC_MSG_ERROR([Cannot find libguththila!]))
AC_CHECK_LIB([axis2_parser],[axiom_xml_reader_init],,AC_MSG_ERROR([Cannot find axis2 libs!]))
AC_CHECK_LIB([axutil],[axutil_url_get_path],,AC_MSG_ERROR([Cannot find libaxutil]))
AC_CHECK_LIB([m],[log10])
AC_CHECK_LIB([mod_rampart],[rampart_mod_create],,AC_MSG_ERROR([Cannot find libmod_rampart]))
AC_CHECK_LIB([neethi],[neethi_engine_get_policy],,AC_MSG_ERROR([Cannot find libneethi!]))
AC_CHECK_LIB([pthread],[pthread_join],,AC_MSG_ERROR([Cannot find pthreads!]))
AC_CHECK_LIB([rt],[timer_gettime])
AC_CHECK_LIB([virt],[virStorageVolDownload],true,AC_MSG_ERROR([Cannot find libvirt!]))
AC_CHECK_LIB([curl],[curl_version_info],true,AC_MSG_ERROR([Cannot find libcurl!]))
AC_CHECK_LIB([z],[inflate])
AC_CHECK_LIB([cap],[cap_from_name],,AC_MSG_WARN([Cannot find sufficiently recent libcap will not use it]))
AC_CHECK_LIB([xml2],[xmlFree],,AC_MSG_ERROR([Cannot find libxml2!]))

# Checks for header files.
AC_HEADER_DIRENT
AC_HEADER_STDC
AC_HEADER_SYS_WAIT
AC_CHECK_HEADERS([fcntl.h limits.h stdint.h stdlib.h string.h strings.h sys/ioctl.h unistd.h sys/vfs.h zlib.h])
AC_CHECK_HEADER([curl/curl.h],,AC_MSG_ERROR([[Cannot find curl/curl.h]]))

# Checks for typedefs, structures, and compiler characteristics.
AC_C_CONST
AC_TYPE_MODE_T
AC_TYPE_PID_T
AC_TYPE_SIZE_T
AC_TYPE_SSIZE_T
AC_HEADER_TIME
AC_STRUCT_TM
AC_TYPE_UINT32_T

# Checks for functions.
AC_FUNC_CLOSEDIR_VOID
AC_FUNC_FORK
AC_FUNC_LSTAT
AC_FUNC_LSTAT_FOLLOWS_SLASHED_SYMLINK
AC_FUNC_MALLOC
AC_FUNC_MEMCMP
AC_FUNC_MKTIME
AC_FUNC_MMAP
AC_FUNC_REALLOC
AC_FUNC_SELECT_ARGTYPES
AC_FUNC_STAT
AC_FUNC_STRNLEN
AC_FUNC_VPRINTF
AC_CHECK_FUNCS([bzero dup2 ftruncate gettimeofday mkdir pow select strchr strdup strerror strncasecmp strstr rmdir xmlFirstElementChild])

# Time to substitute and generate the files
AC_CONFIG_FILES([Makedefs
                 admin-tools/eucalyptus_admin/__init__.py:admin-tools/eucalyptus_admin/__init__.py.in
                 clc/modules/postgresql/src/main/resources/postgresql-binaries.properties:clc/modules/postgresql/src/main/resources/postgresql-binaries.properties.in
                 cluster/eucalyptus-cluster
                 node/eucalyptus-node
                 tools/eucalyptus-cloud:tools/eucalyptus-cloud.in
                 tools/eucalyptus-logrotate.conf:tools/eucalyptus-logrotate.conf.in
                 tools/imaging/setup.cfg.template:tools/imaging/setup.cfg.template.in
                 tools/imaging/eucatoolkit/__init__.py:tools/imaging/eucatoolkit/__init__.py.in
                 clc/modules/core/src/main/resources/version.properties:clc/modules/core/src/main/resources/version.properties.in])

AC_DEFINE_UNQUOTED([EUCA_VERSION], "$EUCA_VERSION", [Version of Eucalyptus])

AC_SUBST(ANT)
AC_SUBST(APACHE2_MODULE_DIR)
AC_SUBST(AXIS2C_HOME)
AC_SUBST(AXIS2C_SERVICES)
AC_SUBST(AXIS2_HOME)
AC_SUBST(CFLAGS)
AC_SUBST(EUCA_DB_HOME)
AC_SUBST(EUCA_DB_SUFFIX)
AC_SUBST(EUCA_DB_OLD_HOME)
AC_SUBST(EUCA_DB_OLD_SUFFIX)
AC_SUBST(EUCA_VERSION)
AC_SUBST(EUCA_VERSION_FULL)
AC_SUBST(EXTRA_VERSION)
AC_SUBST(INCLUDES)
AC_SUBST(INTERFACE_THEME)
AC_SUBST(JAVA_HOME)
AC_SUBST(LDFLAGS)
AC_SUBST(LIBS)
AC_SUBST(LIBVIRT_HOME)
AC_SUBST(PYTHON)
AC_SUBST(RM)
AC_SUBST(TOP)
AC_SUBST(USE_BUNDLED_JARS)
AC_SUBST(WSDL2C)


AC_OUTPUT

dnl Configuration output

echo ""
echo "  Eucalyptus ${EUCA_VERSION} (${EUCA_VERSION_FULL})"
echo ""
echo "  Flags and Settings:"
echo "    CFLAGS..............: ${CFLAGS}"
echo "    LDFLAGS.............: ${LDFLAGS}"
echo "    LIBS................: ${LIBS}"
echo "    Debugging Enabled...: ${DEBUGGING_ENABLED:-no}"
echo "    Use bundled jars....: ${USE_BUNDLED_JARS}"
echo ""
echo "  Build Tools:"
echo "    ANT.................: ${ANT} (${ant_version})"
echo "    JAVA................: ${JAVA} (${java_version})"
echo "    PYTHON..............: ${PYTHON} (${PYTHON_VERSION})"
echo "    WSDL2C..............: ${WSDL2C}"
echo ""
echo "  Locations:"
echo "    PREFIX..............: ${prefix:-Not Set}"
echo "    APACHE2_MODULE_DIR..: ${APACHE2_MODULE_DIR:-Not Set}"
echo "    AXIS2C_HOME.........: ${AXIS2C_HOME:-Not Set}"
echo "    AXIS2C_SERVICES.....: ${AXIS2C_SERVICES:-Not Set}"
echo "    AXIS2_HOME..........: ${AXIS2_HOME:-Not Set}"
echo "    EUCA_DB_HOME........: ${EUCA_DB_HOME:-Not Set}"
echo "    EUCA_DB_SUFFIX......: ${EUCA_DB_SUFFIX:-Not Set}"
echo "    EUCA_DB_OLD_HOME....: ${EUCA_DB_OLD_HOME:-Not Set}"
echo "    EUCA_DB_OLD_SUFFIX..: ${EUCA_DB_OLD_SUFFIX:-Not Set}"
echo "    JAVA_HOME...........: ${JAVA_HOME:-Not Set}"
echo "    LIBVIRT_HOME........: ${LIBVIRT_HOME:-Not Set}"
echo ""

