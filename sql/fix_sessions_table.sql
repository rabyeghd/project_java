-- ============================================
-- Fix Sessions Table Schema
-- ============================================
USE centre_formation;

-- Check if titre column exists and drop it
SET @dbname = 'centre_formation';
SET @tablename = 'sessions';
SET @columnname = 'titre';
SET @preparedStatement = (SELECT IF(
  (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
   WHERE TABLE_SCHEMA = @dbname
   AND TABLE_NAME = @tablename
   AND COLUMN_NAME = @columnname) > 0,
  'ALTER TABLE sessions DROP COLUMN titre;',
  'SELECT ''Column titre does not exist'';'
));
PREPARE alterStatement FROM @preparedStatement;
EXECUTE alterStatement;
DEALLOCATE PREPARE alterStatement;

-- Ensure nom_cours column exists and is properly configured
ALTER TABLE sessions 
MODIFY COLUMN nom_cours VARCHAR(200) NOT NULL;

-- Show the current structure
DESCRIBE sessions;

SELECT 'Sessions table has been fixed!' as Status;
