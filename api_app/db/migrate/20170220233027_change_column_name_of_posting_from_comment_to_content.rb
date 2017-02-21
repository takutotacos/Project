class ChangeColumnNameOfPostingFromCommentToContent < ActiveRecord::Migration[5.0]
  def change
    rename_column :postings, :comment, :content
  end
end
